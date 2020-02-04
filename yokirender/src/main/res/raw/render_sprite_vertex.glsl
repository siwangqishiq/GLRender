#version 300 es

precision mediump float;

uniform mat3 uMatrix;

layout(location = 0) in vec3 aPos;
layout(location = 1) in vec4 aCoord;

out vec2 vPos;
out vec4 vColor;

void main() {
    vec4 pos = vec4( uMatrix * vec3(aPos.xy , 1f) , 1.0f);
    pos.z = aPos.z;
    gl_Position = pos;
}
