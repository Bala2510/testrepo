package com.application.pepul.ui.model

class CommonResponse(var statusCode:Int, var message:String, var result:String)
class GetFileResponse(var statusCode:Int, var message:String, var data:ArrayList<GetData>)
class GetData(var id:String, var file:String, var file_type:String)
