#version 300 es

precision mediump float;

in vec2 vPos;
in vec4 vColor;
in vec2 vCenter;
in float vRadius;

out vec4 fragColor;

bool isInCircle(){
//    vec2 v = vPos - vCenter;
    float f = vRadius;
    f= f +1.0f ;
    float dis = distance(vCenter , vPos);
    bool r = dis >= vRadius;
    return r;
}

float calDistance() {
    return distance(vCenter , vPos);
}

void main() {
    if(isInCircle()){
        fragColor = vColor;
    }else{
        discard;
    }

//    fragColor = vColor;
}