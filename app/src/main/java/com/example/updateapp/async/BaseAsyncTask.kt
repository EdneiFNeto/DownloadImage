package com.example.updateapp.async

import android.os.AsyncTask

class BaseAsyncTask<T>(
    private val executando: () -> T,
    private val callBack: Carregando? = null,
    private val finalizado: (resultado: T) -> Unit
) : AsyncTask<Void, Void, T>() {

    override fun doInBackground(vararg params: Void?): T = executando()

    override fun onPostExecute(result: T) {
        super.onPostExecute(result)
        finalizado(result)
    }

    override fun onPreExecute() {
        super.onPreExecute()
        callBack?.carregando()
    }
}

interface Carregando {
    fun carregando()
}
