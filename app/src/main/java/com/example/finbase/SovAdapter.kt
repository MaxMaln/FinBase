package com.example.finbase

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class SovAdapter(var context: Context, private var sovclass: ArrayList<SovClass>) : BaseAdapter() {

    private class ViewHolder(row: View?){
        var txtNumber: TextView
        var txtName: TextView
        var txtText: TextView
        init {
            this.txtNumber = row?.findViewById(R.id.txtNumber) as TextView
            this.txtName = row.findViewById(R.id.txtName) as TextView
            this.txtText = row.findViewById(R.id.txtText) as TextView
        }
    }

    override fun getCount(): Int {
        return sovclass.count()
    }

    override fun getItem(p0: Int): Any {
        return sovclass[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view: View?
        val viewHolder: ViewHolder
        if(p1 == null)
        {
            val layout = LayoutInflater.from(context)
            view = layout.inflate(R.layout.sovlist_row, p2, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }
        else{
            view = p1
            viewHolder = view.tag as ViewHolder

        }

        val sovclass: SovClass = getItem(p0) as SovClass
        viewHolder.txtNumber.text = sovclass.Number
        viewHolder.txtName.text = sovclass.Name
        viewHolder.txtText.text = sovclass.Text
        return view as View
    }
}