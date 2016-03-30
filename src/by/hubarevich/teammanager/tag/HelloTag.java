/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 */

package by.hubarevich.teammanager.tag;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * TagSupport class. Provides Hello tag logic
 */

@SuppressWarnings("serial")
public class HelloTag extends TagSupport {

    private static final Logger LOG = LogManager.getLogger(HelloTag.class);
    @Override
    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();;
            String to;
                to = "Hello, ";
            out.write(to);
        } catch (IOException e) {
            LOG.warn(e);
        }
        return EVAL_BODY_INCLUDE;
    }


}
