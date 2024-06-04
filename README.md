# WYSIWYG Android

This project is cloned from [Canopas](https://github.com/canopas/)'s [rich-editor-compose](https://github.com/canopas/rich-editor-compose) Repository.

## Description
WYSIWYG provides Composable Rich text editor that allows you to **Bold**, *Italic*, and Underlining a texxt. It converts the Text inside the TextField into JSON that has format data of how the text would be spanned


## Installation
### Add jitpack to your settings.gradle
```Kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io") // This line
    }
}
```
### And the library implementation to your build.gradle
[![](https://jitpack.io/v/rmaprojects/wysiwyg-android.svg)](https://jitpack.io/#rmaprojects/wysiwyg-android)
#### TOML (Latest Android Studio Version [Iguana And Above]):
```toml
[version]
wysiwyg-android = "<latest version here>"

[libraries]
apiResponseWrapper = { module = "com.github.rmaprojects:wysiwyg-android", version.ref = "wysiwyg-android" }

```
```Kotlin
‎ 
implementation(libs.wysiwyg.android)
‎ 
```
#### gradle.kts:
```Kotlin
‎ 
implementation("com.github.rmaprojects:wysiwyg-android:<latest version here>")
‎ 
```

## Sample
For more information, please go to [Sample](https://github.com/rmaprojects/wysiwyg-android/tree/main/sample)

### #1 Create a State
```Kotlin
val richEditorState = remember {
   RichEditorState.Builder()
      .setInput("")
      .adapter(JsonEditorParser())
      .build()
}
```

### #2 Import Style Container and Rich Editor (You don't have to create the Style Container by Yourself)
```Kotlin
StyleContainer(
  state = richEditorState,
  styleButtons = {
    /**
    * You can fill your own StyleButton here, the Bold, Italic, and the Underline one are built in.
    */
  },
)

RichEditor(
  state = richEditorState,
  modifier = Modifier
    .fillMaxWidth()
    .height(128.dp),
  onTextChanged = {
    // You can have your custom logic when user is typing here. Altough you can also have the output of the Rich Editor by simply calling: richEditorState.output()
    // Log.d("OUTPUT", richEditorState.output())
  },
)     
```


