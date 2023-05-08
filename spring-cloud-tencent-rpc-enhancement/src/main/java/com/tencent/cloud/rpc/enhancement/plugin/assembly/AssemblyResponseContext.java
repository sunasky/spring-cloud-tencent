/*
 * Tencent is pleased to support the open source community by making Spring Cloud Tencent available.
 *
 * Copyright (C) 2019 THL A29 Limited, a Tencent company. All rights reserved.
 *
 * Licensed under the BSD 3-Clause License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://opensource.org/licenses/BSD-3-Clause
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.tencent.cloud.rpc.enhancement.plugin.assembly;

import java.util.Collection;

import com.tencent.cloud.rpc.enhancement.plugin.EnhancedResponseContext;
import com.tencent.cloud.rpc.enhancement.plugin.PolarisEnhancedPluginUtils;
import com.tencent.polaris.api.pojo.RetStatus;
import com.tencent.polaris.api.rpc.ResponseContext;

import org.springframework.lang.Nullable;

/**
 * AssemblyResponseContext.
 *
 * @author sean yu
 */
public class AssemblyResponseContext implements ResponseContext {

	private final EnhancedResponseContext responseContext;

	private final Throwable throwable;

	private final RetStatus retStatus;

	public AssemblyResponseContext(@Nullable EnhancedResponseContext responseContext, @Nullable Throwable throwable) {
		this.responseContext = responseContext;
		this.throwable = throwable;
		if (responseContext == null) {
			this.retStatus = PolarisEnhancedPluginUtils.getRetStatusFromRequest(null, null, throwable);
		}
		else {
			this.retStatus = PolarisEnhancedPluginUtils.getRetStatusFromRequest(responseContext.getHttpHeaders(), responseContext.getHttpStatus(), throwable);
		}
	}

	@Override
	public Object getRetCode() {
		if (responseContext == null) {
			return null;
		}
		return this.responseContext.getHttpStatus();
	}

	@Override
	public String getHeader(String key) {
		if (responseContext == null) {
			return null;
		}
		return this.responseContext.getHttpHeaders().getFirst(key);
	}

	@Override
	public Collection<String> listHeaders() {
		if (responseContext == null) {
			return null;
		}
		return this.responseContext.getHttpHeaders().keySet();
	}

	@Override
	public Throwable getThrowable() {
		return this.throwable;
	}

	@Override
	public RetStatus getRetStatus() {
		return this.retStatus;
	}

}
