package at.irfc.app.presentation.di

import at.irfc.app.presentation.program.ProgramDetailViewModel
import at.irfc.app.presentation.program.ProgramViewModel
import at.irfc.app.presentation.voting.VotingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule = module {
    viewModelOf(::ProgramViewModel)
    viewModel { params -> ProgramDetailViewModel(id = params.get(), repository = get()) }
    viewModelOf(::VotingViewModel)
}
