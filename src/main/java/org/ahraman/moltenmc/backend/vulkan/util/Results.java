package org.ahraman.moltenmc.backend.vulkan.util;

import it.unimi.dsi.fastutil.ints.IntSet;
import org.ahraman.moltenmc.backend.vulkan.VulkanException;
import org.jetbrains.annotations.NotNull;

import static org.lwjgl.vulkan.EXTDebugReport.*;
import static org.lwjgl.vulkan.EXTFullScreenExclusive.*;
import static org.lwjgl.vulkan.EXTImageCompressionControl.*;
import static org.lwjgl.vulkan.EXTImageDrmFormatModifier.*;
import static org.lwjgl.vulkan.EXTShaderObject.*;
import static org.lwjgl.vulkan.KHRDeferredHostOperations.*;
import static org.lwjgl.vulkan.KHRDisplaySwapchain.*;
import static org.lwjgl.vulkan.KHRGlobalPriority.*;
import static org.lwjgl.vulkan.KHRSurface.*;
import static org.lwjgl.vulkan.KHRSwapchain.*;
import static org.lwjgl.vulkan.KHRVideoEncodeQueue.*;
import static org.lwjgl.vulkan.KHRVideoQueue.*;
import static org.lwjgl.vulkan.NVGLSLShader.*;
import static org.lwjgl.vulkan.VK10.*;
import static org.lwjgl.vulkan.VK13.*;

public final class Results {
    private static final int VK_PIPELINE_BINARY_MISSING_KHR = 1000483000;
    private static final int VK_ERROR_NOT_ENOUGH_SPACE_KHR = -1000483000;

    private Results() {}

    public static void checked(int result, @NotNull String message) {
        checked(result, message, IntSet.of(VK_SUCCESS));
    }

    public static void checked(int result, @NotNull String message, @NotNull IntSet allowedResults) {
        if (!allowedResults.contains(result)) {
            throw new VulkanException(result, message);
        }
    }

    public static @NotNull String toString(int result) {
        return switch (result) {
            case VK_SUCCESS -> "VK_SUCCESS";
            case VK_NOT_READY -> "VK_NOT_READY";
            case VK_EVENT_SET -> "VK_EVENT_SET";
            case VK_EVENT_RESET -> "VK_EVENT_RESET";
            case VK_INCOMPLETE -> "VK_INCOMPLETE";
            case VK_PIPELINE_COMPILE_REQUIRED -> "VK_PIPELINE_COMPILE_REQUIRED";

            case VK_SUBOPTIMAL_KHR -> "VK_SUBOPTIMAL_KHR";
            case VK_THREAD_IDLE_KHR -> "VK_THREAD_IDLE_KHR";
            case VK_THREAD_DONE_KHR -> "VK_THREAD_DONE_KHR";
            case VK_OPERATION_DEFERRED_KHR -> "VK_OPERATION_DEFERRED_KHR";
            case VK_OPERATION_NOT_DEFERRED_KHR -> "VK_OPERATION_NOT_DEFERRED_KHR";
            case VK_ERROR_INCOMPATIBLE_SHADER_BINARY_EXT -> "VK_INCOMPATIBLE_SHADER_BINARY_EXT";
            case VK_PIPELINE_BINARY_MISSING_KHR -> "VK_PIPELINE_BINARY_MISSING_KHR";

            case VK_ERROR_OUT_OF_HOST_MEMORY -> "VK_ERROR_OUT_OF_HOST_MEMORY";
            case VK_ERROR_OUT_OF_DEVICE_MEMORY -> "VK_ERROR_OUT_OF_DEVICE_MEMORY";
            case VK_ERROR_INITIALIZATION_FAILED -> "VK_ERROR_INITIALIZATION_FAILED";
            case VK_ERROR_DEVICE_LOST -> "VK_ERROR_DEVICE_LOST";
            case VK_ERROR_MEMORY_MAP_FAILED -> "VK_ERROR_MEMORY_MAP_FAILED";
            case VK_ERROR_LAYER_NOT_PRESENT -> "VK_ERROR_LAYER_NOT_PRESENT";
            case VK_ERROR_EXTENSION_NOT_PRESENT -> "VK_ERROR_EXTENSION_NOT_PRESENT";
            case VK_ERROR_FEATURE_NOT_PRESENT -> "VK_ERROR_FEATURE_NOT_PRESENT";
            case VK_ERROR_INCOMPATIBLE_DRIVER -> "VK_ERROR_INCOMPATIBLE_DRIVER";
            case VK_ERROR_TOO_MANY_OBJECTS -> "VK_ERROR_TOO_MANY_OBJECTS";
            case VK_ERROR_FORMAT_NOT_SUPPORTED -> "VK_ERROR_FORMAT_NOT_SUPPORTED";
            case VK_ERROR_FRAGMENTED_POOL -> "VK_ERROR_FRAGMENTED_POOL";
            case VK_ERROR_UNKNOWN -> "VK_ERROR_UNKNOWN";
            case VK_ERROR_VALIDATION_FAILED_EXT -> "VK_ERROR_VALIDATION_FAILED";
            case VK_ERROR_OUT_OF_POOL_MEMORY -> "VK_ERROR_OUT_OF_POOL_MEMORY";
            case VK_ERROR_INVALID_EXTERNAL_HANDLE -> "VK_ERROR_INVALID_EXTERNAL_HANDLE";
            case VK_ERROR_FRAGMENTATION -> "VK_ERROR_FRAGMENTATION";
            case VK_ERROR_INVALID_OPAQUE_CAPTURE_ADDRESS -> "VK_ERROR_INVALID_OPAQUE_CAPTURE_ADDRESS";
            case VK_ERROR_NOT_PERMITTED_KHR -> "VK_ERROR_NOT_PERMITTED";

            case VK_ERROR_SURFACE_LOST_KHR -> "VK_ERROR_SURFACE_LOST_KHR";
            case VK_ERROR_NATIVE_WINDOW_IN_USE_KHR -> "VK_ERROR_NATIVE_WINDOW_IN_USE_KHR";
            case VK_ERROR_OUT_OF_DATE_KHR -> "VK_ERROR_OUT_OF_DATE_KHR";
            case VK_ERROR_INCOMPATIBLE_DISPLAY_KHR -> "VK_ERROR_INCOMPATIBLE_DISPLAY_KHR";
            case VK_ERROR_INVALID_SHADER_NV -> "VK_ERROR_INVALID_SHADER_NV";
            case VK_ERROR_IMAGE_USAGE_NOT_SUPPORTED_KHR -> "VK_ERROR_IMAGE_USAGE_NOT_SUPPORTED_KHR";
            case VK_ERROR_VIDEO_PICTURE_LAYOUT_NOT_SUPPORTED_KHR -> "VK_ERROR_VIDEO_PICTURE_LAYOUT_NOT_SUPPORTED_KHR";
            case VK_ERROR_VIDEO_PROFILE_OPERATION_NOT_SUPPORTED_KHR ->
                "VK_ERROR_VIDEO_PROFILE_OPERATION_NOT_SUPPORTED_KHR";
            case VK_ERROR_VIDEO_PROFILE_FORMAT_NOT_SUPPORTED_KHR -> "VK_ERROR_VIDEO_PROFILE_FORMAT_NOT_SUPPORTED_KHR";
            case VK_ERROR_VIDEO_PROFILE_CODEC_NOT_SUPPORTED_KHR -> "VK_ERROR_VIDEO_PROFILE_CODEC_NOT_SUPPORTED_KHR";
            case VK_ERROR_VIDEO_STD_VERSION_NOT_SUPPORTED_KHR -> "VK_ERROR_VIDEO_STD_VERSION_NOT_SUPPORTED_KHR";
            case VK_ERROR_INVALID_DRM_FORMAT_MODIFIER_PLANE_LAYOUT_EXT ->
                "VK_ERROR_INVALID_DRM_FORMAT_MODIFIER_PLANE_LAYOUT_EXT";
            case VK_ERROR_FULL_SCREEN_EXCLUSIVE_MODE_LOST_EXT -> "VK_ERROR_FULL_SCREEN_EXCLUSIVE_MODE_LOST_EXT";
            case VK_ERROR_INVALID_VIDEO_STD_PARAMETERS_KHR -> "VK_ERROR_INVALID_VIDEO_STD_PARAMETERS_KHR";
            case VK_ERROR_COMPRESSION_EXHAUSTED_EXT -> "VK_ERROR_COMPRESSION_EXHAUSTED_EXT";
            case VK_ERROR_NOT_ENOUGH_SPACE_KHR -> "VK_ERROR_NOT_ENOUGH_SPACE_KHR";

            default -> "Unknown error code (" + result + ")";
        };
    }
}
