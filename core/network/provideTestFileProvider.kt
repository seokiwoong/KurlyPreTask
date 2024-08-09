    @Provides
    @Singleton
    fun provideTestFileProvider(): com.kt.naviagent.network.core.FileProvider =
        com.kt.naviagent.network.core.TestAssetFileProvider()