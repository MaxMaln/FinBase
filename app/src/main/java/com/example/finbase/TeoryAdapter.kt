package com.example.finbase

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class TeoryAdapter(var context: Context, private var teoryclass: ArrayList<TeoryClass>) : BaseAdapter() {

    private class ViewHolder(row: View?){
        var image: ImageView
        var txtNumber: TextView
        var txtName: TextView
        init {
            this.image = row?.findViewById(R.id.image) as ImageView
            this.txtNumber = row.findViewById(R.id.txtNumber) as TextView
            this.txtName = row.findViewById(R.id.txtName) as TextView
        }
    }

    override fun getCount(): Int {
        return teoryclass.count()
    }

    override fun getItem(p0: Int): Any {
        return teoryclass[p0]
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
            view = layout.inflate(R.layout.teorylist_row, p2, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }
        else{
            view = p1
            viewHolder = view.tag as ViewHolder

        }

        val teoryclass: TeoryClass = getItem(p0) as TeoryClass
        viewHolder.image.setImageResource(teoryclass.Image)
        viewHolder.txtNumber.text = teoryclass.Number
        viewHolder.txtName.text = teoryclass.Name

        return view as View
    }
}