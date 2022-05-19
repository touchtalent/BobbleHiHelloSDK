
# Bobble Transliteration SDK

Bobble Transliteration SDK is an input tool that can be integrated into any Android app to enable typing in regional languages. It can be leveraged to bridge the gap between users who prefer mixed scripts and users who prefer pure regional experience. Currently, [73 regional languages](#supported_languages) are supported.

## <a name="setting_up"></a>Setting Up

- Add and initialise BobbleSDK Core in your project. Refer [here](core.md#setup) for steps.

- Add the following dependency in your application module’s build.gradle.
```groovy
implementation 'com.touchtalent.bobblesdk:transliteration'
```

## <a name="apis"></a>Bobble Transliterator APIs

### BobbleTransliterator

1. Managing languages - Transliteration requires few resources to be downloaded from the internet. Install/uninstall the language as per the requirement. 
```kotlin
class SplashActivity : AppCompatActivity {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);
        // Calling this will cause this languages to install, if not already present and others to 
        // uninstall
        BobbleTransliteratorSdk.setLanguages("hi", "bn")
    }

}

```

2. Start transliterating -
Create an instance of ```BobbleTransliterator``` to start a new transliteration session. Pass the language locale (refer [here](#supported_languages) for complete list) in the context of which transliteration needs to take place.

>P.S - The language will be automatically installed if not installed earlier.

```fun transliterate(input: String): String``` - Pass the complete input to get the transliterated output. ```BobbleTransliterator``` evaluates the latest input based on last input and returns results accordingly. 
```java
var transliteration: String;
transliteration = transliterator.transliterate("n");      // transliteration = "न"
transliteration = transliterator.transliterate("na");     // transliteration = "ना"
transliteration = transliterator.transliterate("nam");    // transliteration = "नम"
transliteration = transliterator.transliterate("nama");   // transliteration = "नामा"
transliteration = transliterator.transliterate("namas");  // transliteration = "नमस"
transliteration = transliterator.transliterate("namast"); // transliteration = "नमस्त"
transliteration = transliterator.transliterate("namaste");// transliteration = "नमस्ते" 
```

3. Close BobbleTransliterator object -
The ```BobbleTransliterator``` object must be closed to safely release resources when not required.

```java
class SplashActivity : AppCompatActivity {

    val transliterator: BobbleTransliterator? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);
        transliterator = BobbleTransliterator("hi")
    }
    
    override fun onDestroy(){
        super.onDestroy()
        transliterator?.close()
    }

}

```
## <a name="supported_languages"></a>Supported Languages
|Sl no.| Language name | Language locale |
|---| ------------- | ---------- |
|1|Hindi                | hi_IN         |
|2|Bangla (India)       | bn_IN         |
|3|Marathi              | mr_IN         |
|4|Telugu               | te_IN         |
|5|Tamil (India)        | ta_IN         |
|6|Gujarati             | gu_IN         |
|7|Urdu (India)         | ur_IN         |
|8|Kannada              | kn_IN         |
|9|Odia                 | or_IN         |
|10| Malayalam            | ml_IN         |
|11| Punjabi, Gurmukhi    | pa_IN         |
|12| Assamese             | as_IN         |
|13| Maithili             | mai_IN   |
|14| Sindhi, Devanagari   | sd_IN    |
|15| Kashmiri, Devanagari | ks_IN    |
|16| Dogri, Devanagari    | doi_IN       |
|17| Konkani, Devanagari  | kok_IN   |
|18| Bodo, Devanagari     | brx_IN   |
|19| Sanskrit             | sa_IN         |
|20| Bhojpuri             | bho_IN   |
|21| Marwari (India)      | mwr_IN   |
|22| Nepali (India)       | ne_IN         |
|23| Ahirani              | ahr        |
|24| Awadhi               | awa        |
|25| Bagheli              | bfy        |
|26| Bagri (India)        | bgq\_dev   |
|27| Bagri (Pakistan)     | bgq\_arab  |
|28| Sinhala              | si_LK         |
|29| Tulu                 | tcy        |
|30| Bhili, Devanagari    | bhb\_dev   |
|31| Bhili, Gujarati      | bhb\_gujr  |
|32| Bishnupriya          | bpy\_beng  |
|33| Bodo, Bengali        | brx\_beng  |
|34| Brahui               | brh\_urdu  |
|35| Bundeli              | bns\_dev   |
|36| Chhattisgarhi        | hne\_dev   |
|37| Chittagonian         | ctg\_beng  |
|38| Dhundhari            | dhd\_hindi |
|39| Dogri, Arabic        | doi\_arab  |
|40| Garhwali             | gbm\_dev   |
|41| Garo                 | grt\_beng  |
|42| Godwari              | gdx\_dev   |
|43| Gujari (India)       | gju\_dev   |
|44| Gujari (Pakistan)    | gju\_arab  |
|45| Halbi, Devanagari    | hlb\_dev   |
|46| Halbi, Odia          | hlb\_oria  |
|47| Harauti              | hoj\_dev   |
|48| Haryanvi             | bgc\_dev   |
|49| Kannauji,Transliteration              | bjj\_dev   ||
|50| Kashmiri, Arabic     | ks\_arab   |
|51| Kok Borok, Bengali   | trp\_beng  |
|52| Konkani, Kannada     | kok\_knda  |
|53| Korku                | kfq\_dev   |
|54| Kumaoni              | kfy\_dev   |
|55| Kurukh, Devanagari   | kru\_dev   |
|56| Kurukh, Malayalam    | kru\_mylm  |
|57| Lambadi, Devanagari  | lmn\_dev   |
|58| Lambadi, Telugu      | lmn\_telu  |
|59| Lambadi, Kannada     | lmn\_knda  |
|60| Magahi (India)       | mag\_dev   |
|61| Malvi                | mup\_dev   |
|62| Mandeali             | mjl\_dev   |
|63| Manipuri, Bengali    | mni\_beng  |
|64| Marwari (Pakistan)   | mwr\_arab  |
|65| Mewari               | mtr\_dev   |
|66| Nimadi               | noe\_dev   |
|67| Northern Hindko      | hno\_arab  |
|68| Pahari-Potwari       | phr\_arab  |
|79| Pashto               | ps\_arab   |
|70| Punjabi, Arabic      | pa\_arab   |
|71| Rangpuri, Devanagari | rkt\_dev   |
|72| Santali, Bengali     | sat\_beng  |
|73| Sindhi, Arabic       | sd \_urdu  |
