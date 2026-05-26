#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D DepthSampler;

uniform float STime;

uniform float depthScale = 2.0;
uniform float depthOffset = 0.0;

in vec2 texCoord;
in vec2 oneTexel;

out vec4 fragColor;
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


    vec4 center = texture(DiffuseSampler, texCoord);
    vec4 left = texture(DiffuseSampler, texCoord - vec2(oneTexel.x, 0.0));
    vec4 right = texture(DiffuseSampler, texCoord + vec2(oneTexel.x, 0.0));
    vec4 up = texture(DiffuseSampler, texCoord - vec2(0.0, oneTexel.y));
    vec4 down = texture(DiffuseSampler, texCoord + vec2(0.0, oneTexel.y));
    vec4 leftDiff = center - left;
    vec4 rightDiff = center - right;
    vec4 upDiff = center - up;
    vec4 downDiff = center - down;
    vec4 total = clamp(leftDiff + rightDiff + upDiff + downDiff, 0.0, 1.0);
    if (adjusted < 0) {
        adjusted = 0;
    }
    if (adjusted >= 1 - (STime)) {
        adjusted = 1;
    }else{
        adjusted = 0;
    }
    fragColor = mix(center, vec4(total.rgb, 1) * 2, adjusted);
//    fragColor = vec4(vec3(adjusted),1);
}
