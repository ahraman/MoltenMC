package org.ahraman.moltenmc.backend.vulkan;

import org.ahraman.moltenmc.backend.vulkan.util.Results;

import java.text.MessageFormat;

public class VulkanException extends RuntimeException {
    public final int result;


    public VulkanException(int result, String operation) {
        super(MessageFormat.format("value `{0}` returned by: {1}", Results.toString(result), operation));
        this.result = result;
    }
}
