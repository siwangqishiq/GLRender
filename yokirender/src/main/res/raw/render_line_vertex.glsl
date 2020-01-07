#version 300 es

precision mediump float;

layout(location = 0) in vec2 aPos;
layout(location = 1) in vec4 aColor;

uniform mat3 uMatrix;
out vec4 vColor;

void main() {
    vec4 pos = vec4( uMatrix * vec3(aPos.xy , 1f) , 1.0f);
    pos.z = 0.1f;
    gl_Position = pos;
    vColor = aColor;
}
