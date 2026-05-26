#version 150

uniform sampler2D DepthSampler;
uniform sampler2D DiffuseSampler;
in vec2 texCoord;
out vec4 fragColor;

uniform float depthScale = 2.0;
uniform float depthOffset = 0.0;

vec3 colorA = vec3(1, 1, 1);
vec3 colorB = vec3(1, 0, 0);
uniform vec2 OutSize;
uniform float STime;
float LinearizeDepth(float depth, float near, float far) {
    float z = depth * 2.0 - 1.0; // back to NDC
    return (2.0 * near * far) / (far + near - z * (far - near));
}

void main() {
    float depth = texture(DepthSampler, texCoord).r;
    float linearDepth = LinearizeDepth(depth, 0.1, 100.0); // example near/far
    linearDepth = linearDepth / 100.0; // normalize to 0–1 range
    float inverted = 1.0 - linearDepth;
    float adjusted = pow(inverted, 1.0 / (((1+STime)/2))) + depthOffset;
    adjusted = clamp(adjusted, 0.0, 1.0);
    if (adjusted < 0) {
        adjusted = 0;
    }
    if (adjusted >= 1 - (STime)) {
        adjusted = 1;
    }else{
        adjusted = 0;
    }
    fragColor = texture2D(DiffuseSampler, texCoord);
    vec2 st = gl_FragCoord.xy/OutSize.xy;
    vec3 color = vec3(0.0);

    vec3 pct = vec3(st.x);
    color = mix(colorA, colorB, pct);

    fragColor *= mix(vec4(1),vec4(color,1.0),adjusted);
//    fragColor = vec4(vec3(STime),1);
}
