import com.example.data.responseObjects.surveyListResponse.SurveyAttributesDto

data class SurveyDto(
    val attributes: SurveyAttributesDto,
    val id: String,
    val type: String
)