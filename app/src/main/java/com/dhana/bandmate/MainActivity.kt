package com.dhana.bandmate

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dhana.bandmate.adapter.ListBandAdapter
import com.dhana.bandmate.model.Band


class MainActivity : AppCompatActivity() {
    private lateinit var rvBands: RecyclerView
    private val list = ArrayList<Band>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvBands = findViewById(R.id.rv_bands)
        rvBands.setHasFixedSize(true)

        list.addAll(getListBands())
        showRecyclerList()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.about_page -> {
                val profileIntent = Intent(this@MainActivity, ProfileActivity::class.java)
                startActivity(profileIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getListBands(): ArrayList<Band> {
        val dataId = resources.getIntArray(R.array.data_id)
        val dataName = resources.getStringArray(R.array.data_name)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val listBand = ArrayList<Band>()
        for (i in dataName.indices) {
            val band = Band(dataId[i], dataName[i], dataDescription[i], dataPhoto.getResourceId(i, -1))
            listBand.add(band)
        }
        return listBand
    }

    private fun showRecyclerList() {
        rvBands.layoutManager = GridLayoutManager(this, 2)
        val listBandAdapter = ListBandAdapter(list)
        rvBands.adapter = listBandAdapter
        listBandAdapter.setOnItemClickCallback(object : ListBandAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Band) {
                val detailIntent = Intent(this@MainActivity, DetailActivity::class.java)
                val band = Band(data.id, data.name, data.description, data.photo)
                detailIntent.putExtra(DetailActivity.EXTRA_BAND, band)
                startActivity(detailIntent)
            }
        })
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            (rvBands.layoutManager as GridLayoutManager).spanCount = 3
        } else {
            (rvBands.layoutManager as GridLayoutManager).spanCount = 2
        }
    }

}
