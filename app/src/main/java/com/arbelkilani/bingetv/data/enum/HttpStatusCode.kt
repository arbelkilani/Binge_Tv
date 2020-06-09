package com.arbelkilani.bingetv.data.enum

object HttpStatusCode {

    /**
     * Success
     */
    const val SUCCESS = 1

    /**
     * Invalid service: this service does not exist.
     */
    const val INVALID_SERVICE = 2

    /**
     * Authentication failed: You do not have permissions to access the service.
     */
    const val AUTHENTICATION_FAILED = 3

    /**
     * Invalid format: This service doesn't exist in that format.
     */
    const val INVALID_FORMAT = 4

    /**
     * Invalid parameters: Your request parameters are incorrect.
     */
    const val INVALID_PARAMETERS = 5

    /**
     * 	Invalid id: The pre-requisite id is invalid or not found.
     */
    const val INVALID_ID = 6

    /**
     * Invalid API key: You must be granted a valid key.
     */
    const val INVALID_API_KEY = 7

    /**
     * Duplicate entry: The data you tried to submit already exists.
     */
    const val DUPLICATE_ENTRY = 8

    /**
     * Service offline: This service is temporarily offline, try again later.
     */
    const val SERVICE_OFFLINE = 9

    /**
     * Network error exception
     */
    const val NETWORK_ERROR = -1

    /**
     * Unknown error exception
     */
    const val UNKNOWN_ERROR = -2

    /**
     * Socket timeout exception
     */
    const val SOCKET_TIMEOUT = -3

}