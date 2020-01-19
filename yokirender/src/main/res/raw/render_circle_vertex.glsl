#version 300 es

precision mediump float;

uniform mat3 uMatrix;

layout(location = 0) in vec3 aPos;
layout(location = 1) in vec4 aColor;
layout(location = 2) in float aRadius;
layout(location = 3) in vec2 aCenter;

out vec2 vPos;
out vec4 vColor;
out float vRadius;
out vec2 vCenter;


void main() {
    vec4 pos = vec4( uMatrix * vec3(aPos.xy , 1f) , 1.0f);
    //vec4 center = vec4(uMatrix * vec3(aCenter.xy , 1.0f) , 1.0f);
    pos.z = aPos.z;
    gl_Position = pos;

    vPos = aPos.xy;
    vColor = aColor;
    vRadius = aRadius;
    vCenter = aCenter.xy;
}
