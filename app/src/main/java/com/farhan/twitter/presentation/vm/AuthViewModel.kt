package com.farhan.twitter.presentation.vm

import com.farhan.twitter.data.repository.Repository
import javax.inject.Inject

/**
 * Created by Mohd Farhan on 5/6/2021.
 */
class AuthViewModel @Inject constructor(
    private val repository: Repository
): BaseVM() {

}