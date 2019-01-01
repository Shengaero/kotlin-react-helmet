/*
 * Copyright 2018 Kaidan Gustave
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
module.exports = function(config) {
  config.set({
    basePath: "",
    port: 9876,
    logLevel: config.LOG_INFO,
    browsers: ['Chrome'],
    autoWatch: false,
    singleRun: true,
    client: {
      mocha: {
        bail: false,
        reporter: 'html'
      }
    },

    frameworks: ['mocha', 'browserify'],
    files: ['../build/classes/kotlin/test/kotlin-react-helmet_test.js'],
    preprocessors: {
      '../build/node_modules/*.js': ['browserify'],
      '../build/classes/kotlin/test/*.js': ['browserify'],
      '../build/node_modules/*.js.map': ['sourcemap'],
      '../build/classes/kotlin/test/*.js.map': ['sourcemap']
    },

    browserify: {
      transform: ['babelify']
    },

    coverageReporter: {
      dir: 'coverage/json',
      includeAllSources: true,
      reporters: [
        {
          type: 'json',
          subdir(browser) {
            return browser.toLowerCase().split(/[ /-]/)[0];
          }
        }
      ]
    }
  });
};

