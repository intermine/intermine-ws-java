package org.intermine.webservice.client.exceptions;

/*
 * Copyright (C) 2002-2010 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

import java.net.HttpURLConnection;

import org.intermine.webservice.client.util.HttpConnection;

/**
 * The NotImplementedException is thrown by a service when an
 * attempt is made to send a request that is not supported by the target
 * service.
 *
 * @author Jakub Kulaviak
 */
public class NotImplementedException extends ServiceException
{

    private static final long serialVersionUID = 1L;

    /**
     * @param message message
     */
    public NotImplementedException(String message) {
        super(message);
        initResponseCode();
    }

    /**
     * @param message message
     * @param cause cause
     */
    public NotImplementedException(String message, Throwable cause) {
        super(message, cause);
        initResponseCode();
    }

    /**
     * @param cause cause
     */
    public NotImplementedException(Throwable cause) {
        super(cause);
        initResponseCode();
    }

    /**
     * @param connection connection
     * @see ServiceException for detailed description
     */
    public NotImplementedException(HttpConnection connection) {
        super(connection);
    }

    private void initResponseCode() {
        setHttpErrorCode(HttpURLConnection.HTTP_NOT_IMPLEMENTED);
    }
}
