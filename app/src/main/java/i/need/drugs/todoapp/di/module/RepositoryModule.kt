package i.need.drugs.todoapp.di.module

import dagger.Binds
import dagger.Module
import i.need.drugs.todoapp.data.MainRepositoryImpl
import i.need.drugs.todoapp.data.NotificationRepositoryImpl
import i.need.drugs.todoapp.data.local.LocalDataSourceImpl
import i.need.drugs.todoapp.data.remote.RemoteDataSourceImpl
import i.need.drugs.todoapp.di.ApplicationScope
import i.need.drugs.todoapp.domain.datasource.LocalDataSource
import i.need.drugs.todoapp.domain.datasource.RemoteDataSource
import i.need.drugs.todoapp.domain.notification.NotificationRepository
import i.need.drugs.todoapp.domain.repository.MainRepository


@Module
interface RepositoryModule {

    @Binds
    @ApplicationScope
    fun bindRemoteDataSource(impl: RemoteDataSourceImpl): RemoteDataSource

    @Binds
    @ApplicationScope
    fun bindLocalDataSource(impl: LocalDataSourceImpl): LocalDataSource

    @Binds
    @ApplicationScope
    fun bindMainRepository(impl: MainRepositoryImpl): MainRepository

    @Binds
    @ApplicationScope
    fun bindNotificationRepository(impl: NotificationRepositoryImpl): NotificationRepository

}