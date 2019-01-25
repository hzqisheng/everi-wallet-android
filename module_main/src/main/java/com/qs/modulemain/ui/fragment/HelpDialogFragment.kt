package com.qs.modulemain.ui.fragment

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import com.qs.modulemain.R
import com.qs.modulemain.bean.HelpCenterBean
import com.qs.modulemain.ui.adapter.HelpAdapter

class HelpDialogFragment : DialogFragment() {

    private var list = ArrayList<HelpCenterBean>()
    private var height = 0

    fun setDate(list: List<HelpCenterBean>, height:Int) {
        this.list = list as ArrayList<HelpCenterBean>
        this.height = height
    }

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_top)
        isCancelable = true
    }*/

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(context!!, R.style.dialog_top)
        val v = LayoutInflater.from(context!!).inflate(R.layout.dialog_help, null)
        val recyclerView = v.findViewById<RecyclerView>(R.id.rv_list)
        val adapter = HelpAdapter(list)
        recyclerView.adapter = adapter
        dialog.setContentView(v)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        val window = dialog.window!!
        val lp = window.attributes
        lp.y = height
        lp.gravity = Gravity.TOP
        lp.width = WindowManager.LayoutParams.MATCH_PARENT   //设置宽度充满屏幕
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        window.attributes = lp
        return dialog
    }

    /* override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
         val v = inflater.inflate(R.layout.dialog_help, container, false)
         val recyclerView = v.findViewById<RecyclerView>(R.id.rv_list)
         val adapter = HelpAdapter(list)
         recyclerView.adapter = adapter
         return v
     }*/

}
