package com.example.nikos445.listtestkotlin

import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils.indexOf
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.database.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {


    var database = FirebaseDatabase.getInstance()
    var myRef = database.getReference("Product")
    var Mutl = ArrayList<String>()
    var Mutl2 = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var myList = findViewById<ListView>(R.id.MyLittleList)
        var cont = this
        var Adapter : Adapt

        myRef.addValueEventListener(object : ValueEventListener
        {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for(i in dataSnapshot.children)
                {
                    if(!Mutl.contains(i.key))
                    {
                        Mutl.add(dataSnapshot.child(i.key).key)
                        Mutl2.add(dataSnapshot.child(i.key).value.toString())

                    }

                    if(Mutl.contains(i.key))
                    {
                        Mutl2.set(Mutl.indexOf(i.key),dataSnapshot.child(i.key).value.toString())

                    }

                }
                Adapter=Adapt(cont,Mutl,Mutl2)
                myList.adapter = Adapter
            }

        })


    }

    class Adapt(context: Context, mutl: MutableList<String>, mutl2: ArrayList<String>) : BaseAdapter()
    {
        var context = context
        var Mutl = mutl
        var Mutl2 = mutl2
        override fun getView(position: Int, ConvertView: View?, ViewGroup: ViewGroup?): View
        {

            val view = LayoutInflater.from(context).inflate(R.layout.row_main, ViewGroup, false)
            var Text1 = view.findViewById<TextView>(R.id.Product)
            var Text2 = view.findViewById<Button>(R.id.DeleteButt)
            Text1.text=Mutl.get(position)
            Text2.text=Mutl2.get(position)
            return view
        }

        override fun getItem(position: Int): Any {
            return 0
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return Mutl.size
        }

    }


}
