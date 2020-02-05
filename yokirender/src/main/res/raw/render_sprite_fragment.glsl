#version 300 es

precision mediump float;
uniform sampler2D sTexture;
in vec2 vUv;
out vec4 vColor;

void main() {
    // vColor = vec4(1.0f , 1.0f , 0.0f , 1.0f);
    vColor = texture(sTexture , vUv);
}
