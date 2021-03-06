/*
The MIT License

Copyright (c) 2012-2013, Sylvain Benner.

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 */

package org.jenkinsci.plugins.jobgenerator.parameters;

import hudson.EnvVars;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.export.Exported;

import hudson.model.AbstractBuild;
import hudson.model.StringParameterValue;
import hudson.util.VariableResolver;

/**
 * ParameterValue created from GeneratorKeyValueParameterDefinition.
 * 
 * @author <a href="mailto:sylvain.benner@gmail.com">Sylvain Benner</a>
 */
public class GeneratorKeyValueParameterValue extends StringParameterValue {
    @Exported(visibility = 4)
    public final String value;

    @DataBoundConstructor
    public GeneratorKeyValueParameterValue(String name, String value) {
        this(name, value, null);
    }

    public GeneratorKeyValueParameterValue(String name, String value,
            String description) {
        super(name, description);
        this.value = value;
    }

    /**
     * Exposes the name/value as an environment variable.
     */
    @Override
    public void buildEnvVars(AbstractBuild<?, ?> build, EnvVars env) {
        env.put(name, value);
    }

    @Override
    public VariableResolver<String> createVariableResolver(
            AbstractBuild<?, ?> build) {
        return new VariableResolver<String>() {
            public String resolve(String name) {
                String n = GeneratorKeyValueParameterValue.this.name;
                return n.equals(name) ? value : null;
            }
        };
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        GeneratorKeyValueParameterValue other = 
                                         (GeneratorKeyValueParameterValue) obj;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }

    @Override
    public String toString() {
        String s = "(GeneratorKeyValueParameterValue) " + getName() + "='"
                + value + "'";
        return s;
    }
}
