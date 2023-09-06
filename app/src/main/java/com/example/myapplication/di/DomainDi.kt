package com.example.myapplication.di

import com.example.myapplication.domain.BluetoothDiscovery
import com.example.myapplication.domain.ControllersService
import com.example.myapplication.domain.FlashLight
import org.koin.dsl.module

val controllersModule = module {
    single<FlashLight> {
        FlashLight(context = get())
    }

    single<BluetoothDiscovery> {
        BluetoothDiscovery(context = get())
    }



}