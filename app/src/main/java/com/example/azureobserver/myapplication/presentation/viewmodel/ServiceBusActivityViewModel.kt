package com.example.azureobserver.myapplication.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.azureobserver.myapplication.domain.model.request.AzureMessage
import com.example.azureobserver.myapplication.domain.usecase.GetMessageFromServiceBusUseCase
import com.example.azureobserver.myapplication.domain.usecase.SendMessageToServiceBusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import javax.inject.Inject
import javax.xml.transform.stream.StreamResult


@HiltViewModel
class ServiceBusActivityViewModel @Inject constructor(
    private val getMessageUseCase: GetMessageFromServiceBusUseCase
    , private val sendMessageToServiceBusUseCase: SendMessageToServiceBusUseCase
): ViewModel() {

    private var _messageFromServiceBus = MutableLiveData<AzureMessage>()
    val messageFromServiceBus : LiveData<AzureMessage>
        get() = _messageFromServiceBus

    private var _messagesForRecyclerView = MutableLiveData<List<AzureMessage>>()
    val messagesForRecyclerView: LiveData<List<AzureMessage>>
        get() = _messagesForRecyclerView

    init {
        initRecyclerViewData()
    }

    private fun initRecyclerViewData() {
        _messagesForRecyclerView.value = mutableListOf()
    }
    fun addDataToRecyclerView(){
        _messagesForRecyclerView.value?.plus(messageFromServiceBus.value)
    }

    fun getMessageFromServiceBus(){
        viewModelScope.launch(Dispatchers.IO) {
            val response = getMessageUseCase.invoke()
            if (response.channelTitle!!.isNotEmpty())
            {
                println("la respuesta es ${response}")
//                _messageFromServiceBus.postValue(response)
            }
        }
    }

    fun sendMessageToServiceBus(message: String, ){
        val requestBody: RequestBody = madeRequestForSendMessage(message)
        viewModelScope.launch(Dispatchers.IO){
            try {
                val response = sendMessageToServiceBusUseCase.invoke(requestBody)
            }catch (e : Exception){
                println("There was a problem "+ e.message)
            }
        }
    }

    private fun madeRequestForSendMessage(message: String): RequestBody {
        val requestBodyText =
            """
                <rss version="2.0">
                    <channel>
                        <title>גלובס - פנאי</title>
                        <link>http://www.globes.co.il/news/home.aspx?fid=3317</link>
                        <description>גלובס אתר החדשות בזמן אמת</description>
                        <copyright>גלובס 2018</copyright>
                        <language>he</language>
                        <lastBuildDate>Tue, 03 Jul 2018 15:33:23</lastBuildDate>
                        <ttl>10</ttl>
                        <image>
                            <height>40</height>
                            <width>144</width>
                            <link>http://www.globes.co.il/news/home.aspx?fid=3317</link>
                            <title>זירת העסקים של ישראל</title>
                            <url>http://www.globes.co.il/images/GlobesHE-144x40.gif</url>
                        </image>
                        <item>
                            <title>סרט | "הפנתר השחור": ליהוק מצוין ואקשן סוחף, אבל ארוך מדי</title>
                            <link>http://www.globes.co.il/news/article.aspx?did=1001224805</link>
                            <guid isPermaLink="false">http://www.globes.co.il/document?did=1001224805</guid>
                            <description>"הפנתר השחור" הוא סרט חביב שמכניס את הקהל האפרו-אמריקאי אל היקום של מארוול, רק חבל שהוא כל-כך אר; ביקורת</description>
                            <pubDate>Sun, 25 Feb 2018 13:56:15</pubDate>
                        </item>
                        <item>
                            <title>טלוויזיה | הסדרה "כאן ועכשיו" חסרת כנות באופן קיצוני</title>
                            <link>http://www.globes.co.il/news/article.aspx?did=1001223824</link>
                            <guid isPermaLink="false">http://www.globes.co.il/document?did=1001223824</guid>
                            <description>"כאן ועכשיו" הייתה אמורה להיות סאטירה, אבל מצפייה בארבעת הפרקים הראשונים לא נותרה אלא טרגדיה, בעיקר כי חתום עליה אחד היוצרים המשמעותיים בטלוויזיה - אלן בול ■ ביקורת</description>
                            <author>מיקי לוי</author>
                            <pubDate>Sun, 18 Feb 2018 12:02:17</pubDate>
                        </item>
                        <item>
                            <title>קולנוע | "צורת המים" הוא אחד הסרטים היפים של השנה</title>
                            <link>http://www.globes.co.il/news/article.aspx?did=1001223821</link>
                            <guid isPermaLink="false">http://www.globes.co.il/document?did=1001223821</guid>
                            <description>"צורת המים" עלול היה להפוך למעשייה ילדותית, אבל בידיו של הבמאי גיירמו דל טורו הוא הופך לסרט פנטזיה עוצר נשימה ומרג;■ ביקורת</description>
                            <author>אור סיגולי</author>
                            <pubDate>Sun, 18 Feb 2018 11:56:54</pubDate>
                        </item>
                        <item>
                            <title>תיאטרון | "תדגימי" מגישה למערכת המשפט כתב אישום חמור</title>
                            <link>http://www.globes.co.il/news/article.aspx?did=1001223819</link>
                            <guid isPermaLink="false">http://www.globes.co.il/document?did=1001223819</guid>
                            <description>אחרי הצפייה ב"תדגימי" לא תשאלו יותר למה "היא לא התלוננה קודם", אלא תעריכו את האומץ שלה לעשות זאת</description>
                            <author>רון שוורץ</author>
                            <pubDate>Sun, 18 Feb 2018 11:46:13</pubDate>
                        </item>
                        <item>
                            <title>מוזיקה | האוסף החדש של צביקה פיק מפתיע באיפוק שלו</title>
                            <link>http://www.globes.co.il/news/article.aspx?did=1001223817</link>
                            <guid isPermaLink="false">http://www.globes.co.il/document?did=1001223817</guid>
                            <description>"הלהיטים הגדולים" של צביקה פיק הוא אוסף של כיף, אבל באופן מפתיע גם יודע לדקור את החיוך לפעמ;■ ביקורת</description>
                            <author>תימורה לסינגר</author>
                            <pubDate>Sun, 18 Feb 2018 10:52:59</pubDate>
                        </item>
                        <item>
                            <title>קולנוע | "אני, טוניה" - לא כמו סרטי הספורט שהכרתם בעבר</title>
                            <link>http://www.globes.co.il/news/article.aspx?did=1001224029</link>
                            <guid isPermaLink="false">http://www.globes.co.il/document?did=1001224029</guid>
                            <description>המועמד לשלושה אוסקרים, המביא את סיפורה של המתעמלת האולימפית טוניה הארדינג, לא החליט אם הוא דרמת ספורט ביוגרפית או קומדיית פשע, וזה גם סוד ה;■ ביקורת</description>
                            <author>אור סיגולי</author>
                            <pubDate>Sun, 18 Feb 2018 10:52:36</pubDate>
                        </item>
                        <item>
                            <title>מסעדה | בין סלט שהפך למרק חריף, לבאן-מי גס ומדליק</title>
                            <link>http://www.globes.co.il/news/article.aspx?did=1001222718</link>
                            <guid isPermaLink="false">http://www.globes.co.il/document?did=1001222718</guid>
                            <description>בק-פה האנוי הווייטנאמית, תוספת אופנתית לגל האסייתי, אומנם מעגלים פינות פה ושם, אבל הביקור בשירותים מפצה על הכולsp;■ ביקורת</description>
                            <author>חיליק גורפינקל</author>
                            <pubDate>Sun, 11 Feb 2018 09:34:12</pubDate>
                        </item>
                        <item>
                            <title>מופע | 30 שנה ל"אפר ואבק": פוליקר אפוף בחסד ודמעות</title>
                            <link>http://www.globes.co.il/news/article.aspx?did=1001222751</link>
                            <guid isPermaLink="false">http://www.globes.co.il/document?did=1001222751</guid>
                            <description>p;■ ביקורת</description>
                            <author>תימורה לסינגר</author>
                            <pubDate>Sun, 11 Feb 2018 09:30:13</pubDate>
                        </item>
                        <item>
                            <title>תיאטרון | "ציד המכשפות" מטיפני, צעקני וחוזר על עצמו</title>
                            <link>http://www.globes.co.il/news/article.aspx?did=1001222753</link>
                            <guid isPermaLink="false">http://www.globes.co.il/document?did=1001222753</guid>
                            <description>הבמאי גלעד קמחי מנסה לצקת את ארץ "גלעד" המצמיתה מ"סיפורה של שפחה", אבל התוצאה מאכזבתbsp;■ ביקורת</description>
                            <author>רון שוורץ</author>
                            <pubDate>Sun, 11 Feb 2018 09:28:22</pubDate>
                        </item>
                
                    </channel>
                </rss>"""
        val requestBody: RequestBody =
            RequestBody.create("text/xml".toMediaTypeOrNull(), requestBodyText)
        return requestBody
    }


}



