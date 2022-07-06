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
implementation 'com.touchtalent.bobblesdk:head' // Optional, for head support
```

Sync your Gradle project to ensure that the dependency is downloaded by the build system.

## Required Permissions : 

```xml
<uses-permission android:name="android.permission.CAMERA" />
```
##  Bobble Content APIs:

### BobbleContentView
```BobbleContentView``` imports a complete view showing different formats of contents. The UI is customizable via themes and interaction with content can be captured via listeners. The view handles multiple functionalities :

- Display content and capture user interactions with them.
- Host content stores for browsing content packs.
- Bobble Head creation and management. 

#### i. Add custom view inside a XML layout of your Activity/Fragment
```xml
<com.touchtalent.bobble.content.BobbleContentView
    android:id="@+id/content_view"
    android:layout_width="match_parent"
    android:layout_height="0dp"
    app:layout_constraintBottom_toBottomOf="parent"
    app:layout_constraintTop_toBottomOf="@+id/title" />
```
#### ii. APIs
1. `setShareListener(listener:(uri:Uri) -> Unit)` - Set listener to capture user's interaction with the content.

```kotlin
// BobbleContentView used in the layout activity_content_demo.xml
val bobbleContentView: BobbleContentView = binding.contentView
// Set listener on BobbleContentView to listen for clicks on content share
bobbleContentView.setShareListener { uri ->
    // Uri pointing towards final PNG/WebP/GIF image
}
```
2. `setOtf(text:String)` - Set OTF text for the content - text to personalise the content with.

```kotlin
// BobbleContentView used in the layout activity_content_demo.xml
val bobbleContentView: BobbleContentView = binding.contentView
bobbleContentView.setOtf("Hello")
```

### Bobble Head Creation

- Creation - Bobble Heads can be created via *Full fledged head creation activity*

```kotlin 
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeadDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Create activity contract to launch head creation activity and listen for result
        val activityContract = BobbleHeadSdk.getHeadCreator()
            .newBuilder(this)
            .getActivityContract()

        // Register the contract on activity and set listener.
        // See - https://developer.android.com/training/basics/intents/result
        activityLauncher = registerForActivityResult(activityContract) { createdBobbleHeadId ->
            // Get head id of the new head created, null if head creation failed
            processHead(createdBobbleHeadId)
        }

        binding.createHeadButton.setOnClickListener {
            // Launch head creation activity
            activityLauncher.launch(Unit)
        }
    }
}
```

2. Bobble Heads can also be created using a image file `createBobbleHead(file: File,headCreationCallback: HeadCreationCallback)` - Get Bobble head as a bitmap and file onSuccess Callback, onFailure throw exception
```kotlin
// Callback function for creating Bobble head from image
BobbleHeadSdk.createBobbleHead(
    file,
    object : HeadCreationCallback {
        override fun onSuccess(outputFile: File?, outputBitmap: Bitmap) {
            // do anything with file and bitmap
        }
        override fun onFailure(throwable: Throwable) {
            // handle error
        }

    })
```

- Manage created heads

1. `suspend getHeadById(headId:Long):Result<BobbleHead>` - Get BobbleHead object corresponding the given headId
```kotlin
val headResult: Result<BobbleHead> = BobbleHeadSdk.getHeadManager().getHeadById(headId)
headResult.onSuccess{ bobbleHead -> BobbleHead
    val headDetails:String = buildString {
        append("headId: ${bobbleHead.headId}\n")
        append("width: ${bobbleHead.width}\n")
        append("height: ${bobbleHead.height}\n")
        append("ageGroup: ${bobbleHead.ageGroup}\n")
        append("gender: ${bobbleHead.gender}\n")
    }
}.onFailure{ exception:Exception -> 
    exception.printStackTrace()
}
```
2. `getAllHeads():Flow<List<BobbleHead>>` - Flow to get list of all BobbleHeads created in the SDK. Any change in the head list will cause the Flow to emit a new list.
```kotlin
coroutineScope.launch {
    // Listen for list of heads present in head module, empty list is returned for no heads
    val getHeadsFlow: Flow<List<BobbleHead>> = BobbleHeadSdk.getHeadManager().getAllHeads()
    // Update UI with list of heads
    getHeadsFlow.collectLatest {
        headAdapter.data = it
        binding.noHeadsFound.visibility =
            if (it.isEmpty()) View.VISIBLE else View.GONE
    }
}
```
3. `getPrimaryHeadIdAllHeads():Flow<Long?>` - Flow to get current primary head. Emits null if no head is present. Any change in present head will cause the Flow to emit new value
```kotlin
coroutineScope.launch {
    // Listen for change in primary head within the head module
    val getPrimaryHeadFlow: Flow<Long?> = BobbleHeadSdk.getHeadManager().getPrimaryHeadId()
    // Update UI for primary head
    getPrimaryHeadFlow.collectLatest {
        it?.let { headAdapter.primary = it }
    }
}
```
3. `setPrimaryHead(headId:Long)` - Update current primary head in the SDK. The primary head is used for creating all content. 
```kotlin
BobbleHeadSdk.getHeadManager().setPrimaryHead(selectedHeadId)
```
