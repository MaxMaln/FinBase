package com.example.finbase

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class PracticTypesAdapterList(var context: Context, private var practicClassTypesList: ArrayList<PracticClassTypesList>) : BaseAdapter() {

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
        return practicClassTypesList.count()
    }

    override fun getItem(p0: Int): Any {
        return practicClassTypesList[p0]
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

        val practictypesclass: PracticClassTypesList = getItem(p0) as PracticClassTypesList
        viewHolder.image.setImageResource(practictypesclass.Image)
        viewHolder.txtNumber.text = practictypesclass.Number
        viewHolder.imageprogress.setImageResource(practictypesclass.Image2)
        return view as View
    }
}