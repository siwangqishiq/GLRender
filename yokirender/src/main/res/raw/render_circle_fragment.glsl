#version 300 es

precision mediump float;

in vec2 vPos;
in vec4 vColor;
in vec2 vCenter;
in float vRadius;

out vec4 fragColor;

bool isInCircle(){
    return distance(vPos , vCenter) <= vRadius;
}

//float calDistance() {
//    return distance(vPos , vCenter);
//}

void main() {
    if(isInCircle()){
        fragColor = vColor;
    }else{
        //fragColor = vec4(1.0f , 1.0f , 1.0f , 1.0f);
        discard;
        //fragColor = vec4(vColor.xyz , 0.0f);
    }
}