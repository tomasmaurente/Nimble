import com.example.domain.entities.surveyListResponse.SurveyAttributesDto

data class SurveyDto(
    val attributes: SurveyAttributesDto,
    val id: String,
    val type: String
)