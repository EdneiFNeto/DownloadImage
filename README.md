# Introdução

A funcionalidade do aplicativo está em baixar imagens pela a URL de forma simples, o salvamento das imagens é feita de forma automática, após ser realizado o download. Onde as imagens são gravadas dentro da pasta do aplicativo localizada em “Android/media/com.example.updateapp/file/download”.
Abaixo tem uma imagem animada exibindo todo o passo a passo de como acessar a funcionalidade do app.

## Screenshots

![image1](screenshots/image_animada.gif "Gif animado")

## Material Design

Componentes utilizados do material Design :

- [Text fields][0] - Text fields
- [Buttons][1] - Buttons
- [App bars: top][2] - App bars: top
- [Cards][3] - Cards

[0]: https://material.io/components/text-fields
[1]: https://material.io/components/buttons
[2]: https://material.io/components/app-bars-top
[3]: https://material.io/components/cards

## Bibliotecas utilizadas

- [Recyclerview][4] - Como criar uma lista com o RecyclerView.

## Referências

- [Bitmap][5] - Bitmap
- [BitmapFactory][6] - BitmapFactory
- [URL][7] - URL
- [URLConnection][8] - URLConnection

[5]: https://developer.android.com/reference/android/graphics/Bitmap
[6]: https://developer.android.com/reference/android/graphics/BitmapFactory
[7]: https://developer.android.com/reference/java/net/URL
[8]: https://developer.android.com/reference/java/net/URLConnection

## Funções

Funções realizadas pra baixar e salvar as imagens:

### Download Imagem

```kotlin
fun download(imagem: String?): Bitmap? {
  try {
      var url = URL(imagem)
      var urlConnection = url.openConnection() as HttpURLConnection
      urlConnection.connect()
      var inputStream = urlConnection.inputStream
      return BitmapFactory.decodeStream(inputStream)
  }catch (e: Exception){
      e.printStackTrace()
  }
  return null
}
```

### Salvar Imagem

```kotlin
fun save(bitmap: Bitmap, nameFile: String) {
  var file = File("${getPath}/${nameFile}")
  try {
    if (!file.exists()) {
        if (file?.createNewFile()) {
            var stream = FileOutputStream(file)
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.close()
        } else {
            Log.e("Failed", "Failed save image")
        }
    }
  } catch (e: Exception) {
      e.printStackTrace()
  }
}

```

### Buscar Imagem

```kotlin
fun getImage(): ArrayList<Bitmap>? {

  var images = arrayListOf<Bitmap>()
  try {

    var files = File("$getPath")
    var listFiles = files.listFiles()
    var bitmap: Bitmap? = null

    for (file in listFiles) {
      bitmap = BitmapFactory.decodeFile("${getPath}/${file.name}")
      images.add(bitmap)
    }

    return images
  } catch (e: java.lang.Exception) {
    e.printStackTrace()
  }

  return arrayListOf()
}

private val getPath = context?.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)


```
