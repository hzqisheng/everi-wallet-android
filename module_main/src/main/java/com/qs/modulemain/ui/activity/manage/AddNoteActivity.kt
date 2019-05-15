package com.qs.modulemain.ui.activity.manage

import android.app.Dialog
import android.content.Intent
import android.os.Handler
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import com.qs.modulemain.R
import com.qs.modulemain.bean.GroupNonLeafNode
import com.qs.modulemain.bean.GroupLeafNode
import com.qs.modulemain.ui.activity.index.ScanActivity
import com.qs.modulemain.ui.adapter.ChooseAdapter
import com.smallcat.shenhai.mvpbase.base.SimpleActivity
import com.smallcat.shenhai.mvpbase.extension.toast
import com.smallcat.shenhai.mvpbase.model.WebViewApi
import com.smallcat.shenhai.mvpbase.model.bean.ChooseBean
import com.smallcat.shenhai.mvpbase.model.helper.MessageEvent
import com.smallcat.shenhai.mvpbase.model.helper.RxBus
import com.smallcat.shenhai.mvpbase.model.helper.RxBusCenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_add_note.*
import kotlinx.android.synthetic.main.activity_add_note.ll_name
import kotlinx.android.synthetic.main.activity_add_note.tv_save

class AddNoteActivity : SimpleActivity() {

    private var dialog: Dialog? = null
    private lateinit var list: ArrayList<ChooseBean>
    private var mLeafChoosePosition = 0
    private var mCurrentLevel = 1

    override val layoutId: Int
        get() = R.layout.activity_add_note

    override fun initData() {
        tvTitle?.text = getString(R.string.new_note)

        addSubscribe(RxBus.toObservable(MessageEvent::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    when (it.type) {
                        RxBusCenter.CHECK_ADDRESS -> checkSuccess(it.msg)
                    }
                })

        list = arrayListOf(ChooseBean(getString(R.string.leafNode), true),
                ChooseBean(getString(R.string.nonLeafNode)))
        if (intent.hasExtra("currentLevel")) {
            mCurrentLevel = intent.getIntExtra("currentLevel", 1)
        }
        ll_name.setOnClickListener {
            if (mCurrentLevel < 5) {
                showDialog()
            }
        }
        iv_scan.setOnClickListener {
            val intent = Intent(mContext, ScanActivity::class.java)
            intent.putExtra("ScanType", 1000)
            startActivityForResult(intent, 1)
        }
        iv_threshold_reduce.setOnClickListener {
            try {
                if (et_threshold.text.toString().toInt() <= 1) {
                    return@setOnClickListener
                }
                et_threshold.setText(((et_threshold.text.toString().toInt()) - 1).toString())
            } catch (e: Exception) {
                e.printStackTrace()
                et_threshold.setText("1")
            }
        }
        iv_threshold_add.setOnClickListener {
            try {
                et_threshold.setText(((et_threshold.text.toString().toInt()) + 1).toString())
            } catch (e: Exception) {
                e.printStackTrace()
                et_threshold.setText("1")
            }
        }
        iv_reduce.setOnClickListener {
            try {
                if (et_number.text.toString().toInt() <= 1) {
                    return@setOnClickListener
                }
                et_number.setText(((et_number.text.toString().toInt()) - 1).toString())
            } catch (e: Exception) {
                e.printStackTrace()
                et_number.setText("1")
            }
        }
        iv_add.setOnClickListener {
            try {
                et_number.setText(((et_number.text.toString().toInt()) + 1).toString())
            } catch (e: Exception) {
                e.printStackTrace()
                et_number.setText("1")
            }
        }
        tv_save.setOnClickListener {
            if (mLeafChoosePosition == 0) {
                if (tv_type.text.isNullOrEmpty()) {
                    getString(R.string.please_input_key).toast()
                    return@setOnClickListener
                } else {
                    mWebView.evaluateJavascript(WebViewApi.isValidPublicKey(tv_type.text.toString()), null)
                }
            } else {
                var threshold: Int
                threshold = try {
                    et_threshold.text.toString().toInt()
                } catch (e: Exception) {
                    e.printStackTrace()
                    1
                }
                var weight = getWeight()
                val nonLeapNode = GroupNonLeafNode(threshold, weight)
                intent.putExtra("result", nonLeapNode)
                intent.putExtra("isLeafNode", false)
                setResult(nodeResultCode, intent)
                finish()
            }
        }
    }

    private fun getWeight(): Int {
        return try {
            et_number.text.toString().toInt()
        } catch (e: Exception) {
            e.printStackTrace()
            1
        }
    }

    private fun checkSuccess(string: String) {
        if (string == "true") {
            var weight = getWeight()
            val intent = Intent()
            val leapNode = GroupLeafNode(tv_type.text.toString(), weight)
            intent.putExtra("result", leapNode)
            intent.putExtra("isLeafNode", true)
            setResult(nodeResultCode, intent)
            finish()
        } else {
            getString(R.string.invalid_key).toast()
        }
    }

    private fun showDialog() {
        if (dialog == null) {
            dialog = Dialog(mContext, R.style.CustomDialog)
        }
        val view = LayoutInflater.from(mContext).inflate(R.layout.dialog_group_create, null)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_list)
        val adapter = ChooseAdapter(list)
        adapter.setOnItemClickListener { _, _, position ->
            list[mLeafChoosePosition].isSelected = false
            mLeafChoosePosition = position
            list[mLeafChoosePosition].isSelected = true
            adapter.setNewData(list)
            Handler().postDelayed({
                dialog?.dismiss()
                tv_leaf.text = list[position].title
                if (position == 0) {
                    tv_key.text = getString(R.string.key)
                    v.visibility = View.GONE
                    iv_threshold_reduce.visibility = View.GONE
                    et_threshold.visibility = View.GONE
                    iv_threshold_add.visibility = View.GONE
                    tv_type.visibility = View.VISIBLE
                    iv_scan.visibility = View.VISIBLE
                } else {
                    tv_key.text = getString(R.string.threshold)
                    v.visibility = View.VISIBLE
                    iv_threshold_reduce.visibility = View.VISIBLE
                    et_threshold.visibility = View.VISIBLE
                    iv_threshold_add.visibility = View.VISIBLE
                    tv_type.visibility = View.GONE
                    iv_scan.visibility = View.GONE
                }
            }, 300)
        }
        recyclerView.adapter = adapter
        dialog!!.setContentView(view)
        dialog!!.setCanceledOnTouchOutside(true)
        dialog!!.setCancelable(true)
        dialog!!.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == ScanActivity.resultCode) {
            val result = data!!.getStringExtra("result")
            tv_type.setText(result.toString())
        }
    }

    override fun onDestroy() {
        if (dialog != null && dialog!!.isShowing) {
            dialog!!.dismiss()
        }
        super.onDestroy()
    }

}
