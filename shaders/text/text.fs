#version 330 core

in vec4 vertexColor;
in vec2 textureCoord;

out vec4 fragColor;

uniform sampler2D sampler;

void main()
{
	vec4 texColor = texture(sampler, textureCoord);
	fragColor = texColor * vertexColor;
}