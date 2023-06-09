package com.ian.coru1.utils

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart
import java.text.SimpleDateFormat
import java.util.*


fun String?.isJsonObject():Boolean{
    return (this?.startsWith("{") == true) && this.endsWith("}")
}
fun String?.isJsonArray():Boolean{
    return (this?.startsWith("[") == true) && this.endsWith("]")
}

// edittext listener extension
//(Editable?) -> 반환값을 의미, afterTextChanged 실행후 값 반환을 위해 매개변수 타입 일치시키기
fun EditText.onMyTextChanged(completion : (Editable?) -> Unit){
    this.addTextChangedListener (object :TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            completion(editable)
        }

    })
}

@SuppressLint("SimpleDateFormat")
fun Date.toFormatString() : String {
    val format = SimpleDateFormat("HH:mm:ss")
    return format.format(this)
}

fun EditText.textChangesToFlow(): Flow<CharSequence?> {
    return callbackFlow {
        val listener = object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)=Unit
            override fun afterTextChanged(s: Editable?) =Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                trySend(text)
            }
        }
        addTextChangedListener(listener)

        //콜백이 사라질때 실행되는 메소드
        awaitClose {
            removeTextChangedListener(listener)
        }

    }.onStart {
        emit(text)
    }
}