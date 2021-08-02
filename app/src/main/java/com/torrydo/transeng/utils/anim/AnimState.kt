package com.torrydo.transeng.utils.anim

interface AnimState {

    fun onStart(){}

    fun onFinish(){}

    fun onFailure(){}

    fun onUpdate(float: Float){}

}