package com.application.pepul.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.application.pepul.R
import com.application.pepul.databinding.ActivityMainBinding
import com.application.pepul.service.Status
import com.application.pepul.ui.adapter.DetailsAdapter
import com.application.pepul.ui.model.GetData
import com.application.pepul.ui.model.InputParams
import com.application.pepul.util.Constants
import com.application.pepul.util.PaginationScrollListener
import com.application.pepul.util.viewBinding
import com.application.pepul.view.ImageSelectorDialog
import com.application.pepul.viewmodel.ApiViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class MainActivity : BaseActivity(), ImageSelectorDialog.Action {

    val binding: ActivityMainBinding by viewBinding()

    val apiViewModel : ApiViewModel by viewModels()

    var imageSelectorDialog:ImageSelectorDialog? = null

    var list:ArrayList<GetData> = ArrayList()
    var detailsAdapter: DetailsAdapter? = null

    var lastItem = ""
    var isLastPage: Boolean = false
    var isLoading: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        setOnClickListener()
    }

    private fun initView() {
        binding.hlHeader.imgBack.visibility = View.GONE
        binding.hlHeader.imgPlus.visibility = View.VISIBLE
        binding.hlHeader.txtHeader.text = getString(R.string.app_name)

        val gridLayoutManager = GridLayoutManager(this, 2)
        binding.rvFile.layoutManager = gridLayoutManager
        detailsAdapter = DetailsAdapter(this)
        binding.rvFile.adapter = detailsAdapter

        binding.rvFile.addOnScrollListener(object : PaginationScrollListener(gridLayoutManager){
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                getFileDetails()
            }

        })

        detailsAdapter!!.onItemClick = { model ->
            startActivity(Intent(this,ViewDetailsActivity::class.java).putExtra(Constants.URL,model.file).putExtra(Constants.URL_TYPE,model.file_type))
        }

        detailsAdapter!!.onItemLongClick = { model ->
            val id = model.id
            deleteItem(id,model)
        }

        getFileDetails()
    }

    private fun deleteItem(id: String, model: GetData) {
        val inputParams = InputParams()
        inputParams.id = id

        apiViewModel.deleteFile(inputParams).observe(this, Observer {
            it.let {
                when(it.status){
                    Status.LOADING -> {
                        showProgress()
                    }
                    Status.SUCCESS -> {
                        dismissProgress()
                        if (it.data!!.statusCode.equals(200)){
                            toast(it.data.message)
                            list.remove(model)
                        }else{
                            toast(it.data.message)
                        }
                    }
                    Status.ERROR -> {
                        dismissProgress()
                        toast(it.message!!)
                    }
                }
            }
        })
    }

    private fun getFileDetails() {
        isLoading = false
        val inputParams = InputParams()
        inputParams.lastFetchId = lastItem

        apiViewModel.getFile(inputParams).observe(this, Observer {
            it.let {
                when(it.status){
                    Status.LOADING -> {
                        showProgress()
                    }
                    Status.SUCCESS -> {
                        dismissProgress()
                        if (it.data!!.statusCode.equals(200)){
                            if(it.data.data.isNotEmpty()) {
                                setData(it.data.data)
                            }
                        }else{
                            toast(it.data.message)
                        }
                    }
                    Status.ERROR -> {
                        dismissProgress()
                        toast(it.message!!)
                    }
                }
            }
        })
    }

    private fun setData(listData: ArrayList<GetData>) {
        val size = list.size
        list.addAll(listData)
        val sizeNew = list.size
        Log.d("TAG", "setData: "+list.size)
        detailsAdapter!!.setItems(list,size,sizeNew)
        val s = detailsAdapter!!.itemCount-1
        lastItem = list[s].id
        Log.d("TAG", "initView: "+list[s].id)
    }

    private fun setOnClickListener() {
        binding.hlHeader.imgPlus.setOnClickListener {
            imagePermission {
                imageSelectorDialog = ImageSelectorDialog(this, this)
            }
        }
    }

    override fun onImageSelected(imagePath: String, filename: String) {
        if (sizeCheck(imagePath)){
            uploadFile(imagePath)
        }else{
            toast("Please select image or video from gallery max limit - 200 MB")
        }
    }

    private fun uploadFile(imagePath: String) {
        val inputParams = InputParams()
        inputParams.fileToUpload = imagePath

        apiViewModel.fileUpload(inputParams).observe(this, Observer {
            it.let {
                when(it.status){
                    Status.LOADING -> {
                        showProgress()
                    }
                    Status.SUCCESS -> {
                        dismissProgress()
                        if (it.data!!.statusCode.equals(200)){
                            toast(it.data.message)
                        }else{
                            toast(it.data.message)
                        }
                    }
                    Status.ERROR -> {
                        dismissProgress()
                        toast(it.message!!)
                    }
                }
            }
        })
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            else -> {
                imageSelectorDialog?.processActivityResult(requestCode, resultCode, data)
            }
        }
    }

    fun sizeCheck(imagePath: String):Boolean{
        val path = File(imagePath)
        val fileSizeInBytes = path.length()
        val fileSizeInKB: Long = fileSizeInBytes / 1024
        val fileSizeInMB = fileSizeInKB / 1024
        Log.d("TAG", "sizeCheck: "+fileSizeInMB)
        if (fileSizeInMB <= Constants.MAX_VIDEO_FILE_SIZE_IN_BYTES) {
            return true
        }
        return false
    }

}