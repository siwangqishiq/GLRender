#version 300 es

precision mediump float;

layout(location = 0) in vec3 aPos;
layout(location = 1) in vec4 aColor;
layout(location = 2) in float aSize;

uniform mat3 uMatrix;
out vec4 vColor;

void main() {
    float depth = aPos.z;
    gl_Position = vec4( uMatrix * vec3(aPos.xy , 1.0f) , 1.0f);
    gl_Position.z = depth;
    gl_PointSize = aSize;
    vColor = aColor;
}
