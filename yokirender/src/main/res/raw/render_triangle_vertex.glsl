#version 300 es

precision mediump float;

layout(location = 0) in vec2 aPos;
layout(location = 1) in vec4 aColor;

uniform mat3 uMatrix;
out vec4 vColor;

void main() {
    gl_Position = vec4( uMatrix * vec3(aPos.xy , 1.0f) , 1.0f);
    vColor = aColor;
}
