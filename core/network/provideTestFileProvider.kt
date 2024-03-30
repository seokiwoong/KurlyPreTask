 @Provides
    @Singleton
    fun provideTestFileProvider(): com.kurly.pretask.network.core.FileProvider =
        com.kurly.pretask.network.core.TestAssetFileProvider()