package com.example.simpletodo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.IOException
import org.apache.commons.io.FileUtils;
import java.nio.charset.Charset


class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()
    lateinit var adapter:TaskItemAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener {
        
            override fun onItemLongClicked(position: Int) {
                //remove the item form the list
                listOfTasks.removeAt(position)
                //notify the adapter
                adapter.notifyDataSetChanged()

                saveItems()
            }
        }




        loadItems()
        // Lookup the recyclerview in activity layout
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)
        // Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)
        // Attach the adapter to the recyclerview to populate items
        recyclerview.adapter = adapter
        // Set layout manager to position the items
        recyclerview.layoutManager = LinearLayoutManager(this)
        // That's all!

        //set up the button and input field to enter a task and add it to the list
        val inputTextField = findViewById<EditText>(R.id.addTaskField)
        //get a reference to button and set on clickListener
        findViewById<Button>(R.id.button).setOnClickListener {
            //1.grab the text the user has inputted into addTaskField
            val userInputtedTask = inputTextField.text.toString()
            //2.add the string to our list of tasks: listOfTasks
            listOfTasks.add(userInputtedTask)
            //notify the adapter that the data has been updated
            adapter.notifyItemInserted(listOfTasks.size-1)
            //3. reset text field
            inputTextField.setText("")
        }
    }
    //save the data that user has inputted
    //save the data by writing and reading from a file
    //create a method to get the file
    //get the file we need
    fun getDataFile():File{
        return File(filesDir,"data.txt")
    }
    //load  the items by reading every line in the data file
    fun loadItems(){
        try {
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException:IOException){
            ioException.printStackTrace()
        }
    }
    //save items by writing them into data file
    fun saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), listOfTasks)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }



}
