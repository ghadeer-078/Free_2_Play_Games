package com.example.ghadeers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ghadeers.API.APIClient
import com.example.ghadeers.API.APIInterface
import com.example.ghadeers.Adapter.AdapterApi
import com.example.ghadeers.Database.GameEntity
import com.example.ghadeers.Model.gamesItem
import com.example.ghadeers.ViewModel.myViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class APIFragment : Fragment() {

    lateinit var rvBrowser: RecyclerView
    lateinit var pc: Button
    lateinit var browser: Button
    lateinit var homeBtn: FloatingActionButton

    val GameModel by lazy { ViewModelProvider(this).get(myViewModel::class.java) }
    lateinit var showGames: ArrayList<gamesItem>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_a_p_i, container, false)

        //init
        rvBrowser = view.findViewById(R.id.rvBrowser)
        homeBtn = view.findViewById(R.id.backBtn)
        showGames = arrayListOf()

        //viewModel
        GameModel.getListgame()

        //init UI
        browser = view.findViewById(R.id.browserBtn)
        browser.setOnClickListener {
            //show game platform browser
            requestAPI("browser")
        }

        pc = view.findViewById(R.id.pcBtn)
        pc.setOnClickListener {
            //show game platform pc
            requestAPI("pc")
        }

        //Back to main fragment
        homeBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_APIFragment_to_mainFragment)
        }

        return view
    }

    private fun requestAPI(gameType: String) {
        val apiInterface: APIInterface = APIClient().getClient()!!.create(APIInterface::class.java)
        apiInterface!!.getGames(gameType)!!.enqueue(
            object : Callback<ArrayList<gamesItem>> {
                override fun onResponse(
                    call: Call<ArrayList<gamesItem>>,
                    response: Response<ArrayList<gamesItem>>
                ) {
                    val resource = response.body()
                    updateRv(resource!!)
                }

                override fun onFailure(call: Call<ArrayList<gamesItem>>, t: Throwable) {
                    call.cancel()
                }
            }
        )
    }

    fun updateRv(gametitle: ArrayList<gamesItem>) {
        rvBrowser.adapter = AdapterApi(this, gametitle)
        rvBrowser.layoutManager = LinearLayoutManager(requireContext())
    }

    fun saveToLocal(showData: gamesItem) {
        val show = GameEntity(
            0,
            showData.title,
            showData.platform,
            showData.genre,
            showData.shortDescription,
            showData.thumbnail
        )
        GameModel.addgame(show)
        Toast.makeText(requireContext(), "Game Added ", Toast.LENGTH_SHORT).show()
    }

}