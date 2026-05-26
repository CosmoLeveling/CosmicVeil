#version 330
#define STEPS 800
#define MIN_DIST 0.001
#define MAX_DIST 2500.

uniform sampler2D DiffuseSampler;
uniform sampler2D DepthSampler;
uniform mat4 InverseTransformMatrix;
uniform mat4 ModelViewMat;
uniform vec3 CameraPosition;
uniform vec3 BlockPosition;

uniform vec2 OutSize;

in vec2 oneTexel;
in vec2 texCoord;

out vec4 fragColor;

float sdShape(vec3 p, vec2 s) {
    return length(p + vec3(.5)) - s.x / .25;
}

vec2 raycast(vec3 point, vec3 dir) {
    float traveled = 0.;
    int close_steps = 0;
    for (int i = 0; i < STEPS; i++) {
        float safe = sdShape(point, vec2(1, 1));
        if (safe <= MIN_DIST || traveled >= MAX_DIST) break;
        traveled += safe;
        point += dir * safe;
        if (safe <= 0.01) close_steps += 1;
    }
    return vec2(traveled, close_steps);
}

vec3 colorA = vec3(1, 1, 1);
vec3 colorB = vec3(1, 0, 0);

vec3 worldPos(vec3 point) {
    vec3 ndc = point * 2.0 - 1.0;
    vec4 homPos = InverseTransformMatrix * vec4(ndc, 1.0);
    vec3 viewPos = homPos.xyz / homPos.w;
    return (inverse(ModelViewMat) * vec4(viewPos, 1.)).xyz + CameraPosition;
}

void main() {
    vec4 original = texture(DiffuseSampler, texCoord);
    float depth = texture(DepthSampler, texCoord).r;
    // Reconstruct start/end world positions
    vec3 start_point = worldPos(vec3(texCoord, 0)) - BlockPosition;
    vec3 end_point = worldPos(vec3(texCoord, depth)) - BlockPosition;
    vec3 dir = normalize(end_point - start_point);

    vec2 hit_result = raycast(start_point, dir);
    vec3 hit_point = start_point + dir * hit_result.x;

    // Edge detection
    vec4 center = texture(DiffuseSampler, texCoord);
    vec4 left = texture(DiffuseSampler, texCoord - vec2(oneTexel.x, 0.0));
    vec4 right = texture(DiffuseSampler, texCoord + vec2(oneTexel.x, 0.0));
    vec4 up = texture(DiffuseSampler, texCoord - vec2(0.0, oneTexel.y));
    vec4 down = texture(DiffuseSampler, texCoord + vec2(0.0, oneTexel.y));
    vec4 total = clamp((center - left) + (center - right) + (center - up) + (center - down), 0.0, 1.0);

    // Gradient mix
    vec2 st = gl_FragCoord.xy / OutSize.xy;
    vec3 col = total.rgb * mix(vec3(1.0), vec3(1.0, 0.0, 0.0), st.x);

    // --- Shape distance ---
    float shapeDist = sdShape(end_point, vec2(1.0, 1.0));

    // --- Edge calculation ---
    float edgeThickness = 0.05;
    float edge = smoothstep(edgeThickness, 0.0, abs(shapeDist)); // outline band

    // --- Fill (optional interior color) ---
    float interior = 1 - smoothstep(0.1, 0.0, shapeDist);

    // Combine edge + fill
    float threshold = 1 - max(interior * 0.7, edge); // edge stronger, interior dimmer
    if (interior >= 1) {
        threshold = 0;
    }
    // Blend with original color
    fragColor = vec4(mix(original.xyz, col, threshold), 1.0);
    //    fragColor = original;
    //    fragColor = vec4(vec3(threshold),1.0);
}
