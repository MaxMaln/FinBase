package com.example.finbase

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class UrlAdapter(var context: Context, private var urlclass: ArrayList<UrlClass>) : BaseAdapter() {

    private class ViewHolder(row: View?){
        var txtUrl: TextView
        var txtName: TextView
        init {
            this.txtUrl = row?.findViewById(R.id.txtUrl) as TextView
            this.txtName = row.findViewById(R.id.txtName) as TextView
        }
    }

    override fun getCount(): Int {
        return urlclass.count()
    }

    override fun getItem(p0: Int): Any {
        return urlclass[p0]
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
            view = layout.inflate(R.layout.urllist_row, p2, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }
        else{
            view = p1
            viewHolder = view.tag as ViewHolder

        }

        val urlclass: UrlClass = getItem(p0) as UrlClass
        viewHolder.txtUrl.text = urlclass.Url
        viewHolder.txtName.text = urlclass.Name
        return view as View
    }
}