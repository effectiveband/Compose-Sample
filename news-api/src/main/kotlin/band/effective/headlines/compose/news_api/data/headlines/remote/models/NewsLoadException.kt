package band.effective.headlines.compose.news_api.data.headlines.remote.models

import band.effective.headlines.compose.core.entity.ErrorReason

class NewsLoadException(val reason: ErrorReason): Exception()
