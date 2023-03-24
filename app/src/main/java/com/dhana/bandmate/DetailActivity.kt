package com.dhana.bandmate

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dhana.bandmate.adapter.ListBandMemberAdapter
import com.dhana.bandmate.model.Band
import com.dhana.bandmate.model.BandMember

class DetailActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val EXTRA_BAND = "extra_band"
    }

    private lateinit var rvBandMembers: RecyclerView
    private val list = ArrayList<BandMember>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val bandId = getBandDetails()

        rvBandMembers = findViewById(R.id.rv_band_members)
        rvBandMembers.setHasFixedSize(true)

        list.addAll(getListBandMembers(bandId))
        showRecyclerList()

        val btnShare: Button = findViewById(R.id.action_share)
        btnShare.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.action_share -> {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, getSharableContent())
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getListBandMembers(id: Int): ArrayList<BandMember> {
        val dataName = resources.getStringArray(R.array.data_members)[id].split(',')
        val dataPosition = resources.getStringArray(R.array.data_positions)[id].split(',')
        val listBandMembers = ArrayList<BandMember>()
        for (i in dataName.indices) {
            val member = BandMember(dataName[i], dataPosition[i])
            listBandMembers.add(member)
        }
        return listBandMembers
    }

    private fun showRecyclerList() {
        rvBandMembers.layoutManager = LinearLayoutManager(this)
        val listBandMemberAdapter = ListBandMemberAdapter(list)
        rvBandMembers.adapter = listBandMemberAdapter
    }


    private fun getBandDetails(): Int {
        val bandNameTextView: TextView = findViewById(R.id.detail_band_name)
        val bandImageView: ImageView = findViewById(R.id.detail_band_photo)
        val bandDescTextView: TextView = findViewById(R.id.detail_band_desc)

        val band = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_BAND, Band::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_BAND)
        }
        if (band != null) {
            bandImageView.setImageResource(band.photo)
            bandNameTextView.text = band.name
            bandDescTextView.text = band.description
        }
        return band?.id ?: 0
    }

    private fun getSharableContent(): String {
        val band = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_BAND, Band::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_BAND)
        }

        if (band != null) {
            return "Kamu harus mendengarkan ${band.name}"
        }
        return "Kamu harus mendengarkan Band Ini"
    }
}