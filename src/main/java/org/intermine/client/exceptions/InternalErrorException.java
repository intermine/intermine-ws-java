package org.intermine.client.exceptions;

/*
 * Copyright (C) 2002-2019 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

import org.intermine.client.util.HttpConnection;

import java.net.HttpURLConnection;



/**
 * The InternalErrorException is thrown by service when an error occurred at the server.
 *
 * @author Jakub Kulaviak
 */
public class InternalErrorException extends ServiceException
{

    private static final long serialVersionUID = 1L;

    /**
     * @param message message
     */
    public InternalErrorException(String message) {
        super(message);
    }

    /**
     * @param message message
     * @param cause cause
     */
    public InternalErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param cause cause
     */
    public InternalErrorException(Throwable cause) {
        super(cause);
    }

    /**
     * @param connection connection
     * @see ServiceException for detailed description
     */
    public InternalErrorException(HttpConnection connection) {
        super(connection);
    }

    @Override
    public int getHttpErrorCode() {
        return HttpURLConnection.HTTP_INTERNAL_ERROR;
    }
}
