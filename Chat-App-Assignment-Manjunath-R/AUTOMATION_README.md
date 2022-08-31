## CHAT APP AUTOMATION ASSIGNMENT 
After downloading the code from git(https://github.com/adrianbudzynski/Chat-App-Android-Demo-Project) and running it in android studio, I have been getting permission denied database error after logging-into app and seeing empty screens on navigating to any bottom nav tabs because of no data.
In order to get start with my automation , i made changes in the application source code especially in the viewmodels like chatsViewModel, profileViewModel and userViewModel where i'm returning static data rather than getting it from remote DB.

## Below are the code changes made in the application source code-
## ChatsViewModel ->
val chatData: MutableList<ChatWithUserInfo>
    get() {
        val list = ArrayList<ChatWithUserInfo>()
        list.add(ChatWithUserInfo(
        Chat(Message("1234", "Chat", 1661072523000)),
        UserInfo(id = "12345",
        displayName = "TestUser",)))
    return list
}

init {
    chatsList.value = chatData
    chatsList.addSource(_updatedChatWithUserInfo) { newChat ->
    val chat = chatsList.value?.find { it.mChat.info.id == newChat.mChat.info.id }
    if (chat == null) {
        chatsList.addNewItem(newChat)
    } else {
    chatsList.updateItemAt(newChat, chatsList.value!!.indexOf(chat))
    } }
    setupChats()
}

## UsersViewModel ->
var userData:List<User>? = null
    get(){
        val list = ArrayList<User>()
        list.add(User(
        UserInfo(
        id = "12345",
        displayName = "TestUser",
        status = "test1")))
        list.add(User(
        UserInfo(
            id = "54321",
            displayName = "testaccount",
            status = "test2"
        )))
    return list
}

init {
    usersList.value = userData
    usersList.addSource(updatedUsersList) { mutableList ->
    userData = updatedUsersList.value?.filter { it.info.id != myUserID }
    }
    loadUsers()
}

## ProfileViewModel ->
var layoutStateData:LayoutState? = null
    get(){
        val data = LayoutState.REQUEST_SENT
    return data
}

var userData:List<User>? = null
    get(){
        val list = ArrayList<User>()
        list.add(User(
        UserInfo(
            id = "12345",
            displayName = "TestUser",
        status = "test1"
    )))
    list.add(User(
    UserInfo(
    id = "54321",
    displayName = "testaccount",
    status = "test2"
    )))
    return list
}

fun setupProfile() {
    _otherUser.value = userData!!.filter {
    it.info.id == userID
    }!!.first()
    layoutState.value = layoutStateData
}

## SettingsFragment ->
private fun showEditStatusDialog() {
    val input = EditText(requireActivity() as Context)
    input.hint = " new status"
    AlertDialog.Builder(requireActivity()).apply {
    setTitle("Status:")
    setView(input)
    setPositiveButton("Ok") { _, _ ->
    val textInput = input.text.toString()
    if (!textInput.isBlank() && textInput.length <= 40) {
//    viewModel.changeUserStatus(textInput)
    viewModel._userInfo.value = UserInfo(
    id = "12345",
    displayName = "TestUser",
    status = textInput
    )} }
    setNegativeButton("Cancel") { _, _ -> }
    show()
    }
}


## SettingsViewModel ->
fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }
//    private val _userInfo: MutableLiveData<UserInfo> = MutableLiveData()
    val _userInfo = MutableLiveData<UserInfo>().default(
    UserInfo(
        id = "12345",
        displayName = "TestUser",
        status = "test1"
    )
)

## Made change in the AuthRepository class to implement espresso idling resource
## without idling resource test will fail on clicking login 
## because in the background thread is fetching data from firebase db
//Espresso idling resource
EspressoIdlingResource.increment()
    firebaseAuthService.loginWithEmailAndPassword(login).addOnSuccessListener {
    EspressoIdlingResource.decrement()

added EspressoIdlingResource singleton class in the path of app/src/main/java/com/fredrikbogg/android_chat_app/util/EspressoIdlingResource.kt



## Espresso Automation -
I have used [Robot Patterns] with kotlin features like [scope functions(apply) and Kotlin extensions] to build the UI test.
I have have built a small framework inside androidTest directory and structured the framework to use testData from json file using Moshi library.
For each screen there is robot written and for each robot there is robot result
[withRobotName - is used to initialise the robot]

[verifyThat - is kotlin extension extending robot with robot result and used for assertion]

## Observations found during automation -
[Verifying bottom navigation screen title] - on clicking any bottom navigation tabs(chats,users,settings,notifications) title of the screen is getting appended with description attribute.
I found this by getting view hierarchy.
View hierarchy ->
BottomNavigationItemView{id=2131362031, res-name=navigation_chats, desc=Chats, visibility=VISIBLE, width=270, height=154, has-focus=false, has-focusable=true, has-window-focus=true, is-clickable=true, is-enabled=true, is-focused=false, is-focusable=true, is-layout-requested=false, is-selected=true, layout-params=android.view.ViewGroup$LayoutParams@9e6a2b0, tag=null, root-is-layout-requested=false, has-input-connection=false, x=0.0, y=0.0, child-count=2}

[Change status alert dialog] - I was unable to identify and enter value in the alert dialog.
From the view hierarchy i got the resource id of the alert dialog but was not able to identify via Espresso using resource Id , not sure what is the issue here. I think default android provided alert dialog are not getting detected by Espresso using resource id.
View hierarchy ->
[EditText inside alert dialog has no resource id] ->
EditText{id=-1, visibility=VISIBLE, width=936, height=124, has-focus=false, has-focusable=true, has-window-focus=true, is-clickable=true, is-enabled=true, is-focused=false, is-focusable=true, is-layout-requested=false, is-selected=false, layout-params=android.widget.FrameLayout$LayoutParams@5a8906a, tag=null, root-is-layout-requested=false, has-input-connection=true, editor-info=[inputType=0x20001 imeOptions=0x40000006 privateImeOptions=null actionLabel=null actionId=0 initialSelStart=0 initialSelEnd=0 initialCapsMode=0x0 hintText= new status label=null packageName=null autofillId=null fieldId=0 fieldName=null extras=null hintLocales=null contentMimeTypes=null ], x=0.0, y=0.0, text=, hint= new status, input-type=131073, ime-target=false, has-links=false}
[I was not able to click with below alert dialog resource id]
AlertDialogLayout{id=2131362056, res-name=parentPanel, visibility=VISIBLE, width=936, height=412, has-focus=false, has-focusable=true, has-window-focus=true, is-clickable=false, is-enabled=true, is-focused=false, is-focusable=false, is-layout-requested=false, is-selected=false, layout-params=android.widget.FrameLayout$LayoutParams@369c50a, tag=null, root-is-layout-requested=false, has-input-connection=false, x=0.0, y=0.0, child-count=4}

[Hence i added hint by name “new status” to the edit text field] with below code changes.

private fun showEditStatusDialog() {
    val input = EditText(requireActivity() as Context)
    input.hint = " new status"
    AlertDialog.Builder(requireActivity()).apply {
    setTitle("Status:")
    setView(input)
[With the above changes, i was able to identify and click edit text.]

## Automation Report -
Automated 9 tests covering all the 4 use cases aforementioned in the email.
Please find the attached junit report of all 9 tests including screenshot of command line run.
[Report path  - Chat-App-Assignment-Manjunath-R/app/build/reports/androidTests/connected/index.html]

##How to run automated testcases -
go to the project folder and run below command

./gradlew connectedAndroidTest


 


