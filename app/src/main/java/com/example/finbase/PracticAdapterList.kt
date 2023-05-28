package com.example.finbase

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class PracticAdapterList(var context: Context, private var practicclasslist: ArrayList<PracticClassList>) : BaseAdapter() {

    private class ViewHolder(row: View?){
        var image: ImageView
        var txtNumber: TextView
        var imageprogress: ImageView
        init {
            this.image = row?.findViewById(R.id.image) as ImageView
            this.txtNumber = row.findViewById(R.id.txtNumber) as TextView
            this.imageprogress = row.findViewById(R.id.imageprogress) as ImageView
        }
    }

    override fun getCount(): Int {
        return practicclasslist.count()
    }

    override fun getItem(p0: Int): Any {
        return practicclasslist[p0]
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
            view = layout.inflate(R.layout.practiclist_row, p2, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }
        else{
            view = p1
            viewHolder = view.tag as ViewHolder

        }

        val practicclass: PracticClassList = getItem(p0) as PracticClassList
        viewHolder.image.setImageResource(practicclass.Image)
        viewHolder.txtNumber.text = practicclass.Number
        viewHolder.imageprogress.setImageResource(practicclass.Image2)
        return view as View
    }
}