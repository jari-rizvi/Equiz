package com.teamx.equiz.ui.fragments.quizresult.data
import androidx.annotation.Keep

@Keep
data class QuizScoreData(
    val __v: Int,
    val _id: String,
    val quizId: QuizId,
    val score: Double,
    val userId: User
)


@Keep
data class User(val name:String)
@Keep
data class QuizId(val title:String)
/*
{
    "populatedQuizScoreData": {
    "_id": "659512260880df346e5d23f3",
    "userId": {
        "name": "Faiq bhai."
    },
    "quizId": null,
    "score": 100,
    "__v": 0
},
    "chances": 2
}*/
