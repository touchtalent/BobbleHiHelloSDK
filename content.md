 #  Bobble Content SDK

Bobble Content SDK provides an all in one solution for your content requirements:
1. Creating Bobble Heads (Cartoonised Avatar heads from user's selfie)
2. Stickers
3. Animated Stickers
4. Regional GIFs.

The SDK hosts a massive repository with more than 100K+ content.

> *OTF - OTF refers to <b>On The Fly</b>, which is a capability of adding on-demand text layer within a content to give it a personalised touch.
## <a name="implementation_steps"></a>Implementation Steps

- Add and initialise BobbleSDK Core in your project. Refer [here](core.md#setup) for steps.

- Add following dependency in your application module’s build.gradle.
```groovy
implementation 'com.touchtalent.bobblesdk:content'
implementation 'com.touchtalent.bobblesdk:heads' // Optional, for head support
```

Sync your Gradle project to ensure that the dependency is downloaded by the build system.

## Required Permissions : 

```xml
<uses-permission android:name="android.permission.CAMERA" />
```
##  Bobble Content APIs:

### BobbleContentSuggestionView
```BobbleContentSuggestionView``` imports a complete view showing different formats of contents. The UI is customizable via themes and interaction with content can be captured via listeners. The view handles multiple functionalities :

- Display content and capture user interactions with them.
- Host content stores for browsing content packs.
- Bobble Head creation and management. 

#### i. Add custom view inside a XML layout of your Activity/Fragment
```xml
<com.touchtalent.bobblesdk.content.BobbleContenSuggestionView
    android:id="@+id/content_suggestion_view"
    android:layout_width="match_parent"
    android:layout_height="200dp"/>
```
#### ii. APIs
Add listeners to capture user's interaction with the content.

```kotlin
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bindEditText(binding.editText) // Bind the view with EditText to personalise based on input text
        binding.contentSuggestionView.setContentListener{ uri ->
            //Called when user taps on any of the content.
            //The uri points to the local path of the content that user tapped on.
        }
    }
}
```

### Bobble Head Creation

- Creation - Bobble Heads can be created via *Full fledged head creation activity (includes UI)*

```kotlin 
class MainActivity : AppCompatActivity() {

    fun launchHeadCreationActivity(){
        BobbleHeadSdk.getHeadCreator()
            .newBuilder(this)
            .startActivityForResult(this, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === REQUEST_CODE) {
            if (resultCode === BobbleHeadSdk.RESULT_SUCCESS) {
                val head: BobbleHead = BobbleHead.fromIntentResult(data)
                val headId: Long = head.characterId //Unique id (local) of head.
                val width: Int = head.width // Width of Bobble head.
                val height: Int = head.height // Height of Bobble head.
                val headImage: String = head.headImage // Returns URI of PNG image pointing to the Bobble Head.
                val rawImage: String = head.rawImage // Get raw PNG image used for head creation
            }
        } 
    }
}
```

- Manage created heads

```kotlin
//Fetch created heads
val heads: Result<List<BobbleHead> = BobbleHeadSdk.getHeadManager().getAll() // Suspend function to fetch all heads

//Delete a head
val headIdToBeDeleted = getHeadIdToBeDeleted()
BobbleHeadSdk.getHeadManager().delete(headIdToBeDeleted)
```
