# Introdução

A funcionalidade do aplicativo está em baixar imagens pela a URL de forma simples, o salvamento das imagens é feita de forma automática, após ser realizado o download. Onde as imagens são gravadas dentro da pasta do aplicativo localizada em “Android/media/com.example.updateapp/file/download”.
Abaixo tem uma imagem animada exibindo todo o passo a passo de como acessar a funcionalidade do app.

## Screenshots

![image1](screenshots/image_animada.gif "Gif animado")

## Material Design

Componentes utilizados do material Design :

- [Text fields](https://material.io/components/text-fields) - Text fields
- [Buttons](https://material.io/components/buttons) - Buttons
- [App bars: top](https://material.io/components/app-bars-top) - App bars: top
- [Cards](https://material.io/components/cards) - Cards

## Bibliotecas utilizadas

- [Recyclerview](https://developer.android.com/guide/topics/ui/layout/recyclerview) - Como criar uma lista com o RecyclerView.

## Referências

- [Bitmap](https://developer.android.com/reference/android/graphics/Bitmap) - Bitmap
- [BitmapFactory](https://developer.android.com/reference/android/graphics/BitmapFactory) - BitmapFactory
- [URL](https://developer.android.com/reference/java/net/URL) - URL
- [URLConnection](https://developer.android.com/reference/java/net/URLConnection) - URLConnection

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
